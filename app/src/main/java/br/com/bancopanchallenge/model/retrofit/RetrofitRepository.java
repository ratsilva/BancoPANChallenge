package br.com.bancopanchallenge.model.retrofit;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.com.bancopanchallenge.model.Game;
import br.com.bancopanchallenge.model.room.RoomRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class RetrofitRepository {

    private MutableLiveData<Status> statusAPI = new MutableLiveData<>();

    private GameService gameService = new RetrofitConfig().getGameService();
    private RoomRepository roomRepository;

    private final String CLIENTE_ID = "1it3vh6uu37zjp5zroevek2tmgfonr";
    private final String LIMIT_GAMES = "10";

    public RetrofitRepository(){
        roomRepository = new RoomRepository();
        gameService = new RetrofitConfig().getGameService();
        statusAPI.postValue(Status.SUCCESS);
    }

    public LiveData<Status> getStatusAPI(){
        return statusAPI;
    }

    public void getAllGames(int offset){

        statusAPI.postValue(Status.LOADING);

        Call<ResponseGson> listGames = gameService.getTopGames(CLIENTE_ID, String.valueOf(offset));

        listGames.enqueue(new Callback<ResponseGson>() {

            @Override
            public void onResponse(Call<ResponseGson> call, Response<ResponseGson> response) {

                Game game = null;
                List<Game> listGames = new ArrayList<>();

                ResponseGson gameGson = response.body();
                List<ResponseGson.Top> listTop = gameGson.getTop();

                for(ResponseGson.Top rp : listTop){
                    game = new Game(rp.getViewers(), rp.getChannels(), rp.getGame().getLocalized_name(), rp.getGame().getBox().get("large"));
                    listGames.add(game);
                }

                roomRepository.deleteAllGames();
                roomRepository.insertAllGames(listGames);

                statusAPI.postValue(Status.SUCCESS);

            }

            @Override
            public void onFailure(Call<ResponseGson> call, Throwable t) {
                // tratar algum erro
                Log.v("failure", ""+t.getMessage());
                statusAPI.postValue(Status.FAIL);
            }
        });


    }

}
