package holdMyBeer.query.controller;

import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.IngredientDB;
import io.grpc.ManagedChannel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {

    private final BeerServiceGrpc.BeerServiceBlockingStub blockingStub;

    public BeerQueryController(ManagedChannel channel) {
        this.blockingStub = BeerServiceGrpc.newBlockingStub(channel);
    }

    public List<IngredientDB> convertIngredientQueryResponseToDB(List<IngredientQueryResponse> ingredientsRequest){
        List<IngredientDB> ingredientsDB = new ArrayList<>();
        for (IngredientQueryResponse ingredient : ingredientsRequest) {
            IngredientDB newIngredient = new IngredientDB();
            newIngredient.setName(ingredient.getName());
            newIngredient.setQuantity(ingredient.getQuantity());
            newIngredient.setUnit(ingredient.getUnit());
            ingredientsDB.add(newIngredient);
        }

        return ingredientsDB;

    }

    public List<BeerDB> convertBeerQueryResponseToBeerDB(List<BeerQueryResponse> beersRequest){
        List<BeerDB> beersDB = new ArrayList<>();
        for (BeerQueryResponse beerUser : beersRequest) {
            BeerDB beerDB = new BeerDB();
            beerDB.set_id(beerUser.getId());
            beerDB.setName(beerUser.getName());
            beerDB.setDescription(beerUser.getDescription());
            beerDB.setIngredients(convertIngredientQueryResponseToDB(beerUser.getIngredientsList()));
            beerDB.setMethods(beerUser.getMethodsList().toArray(new String[0]));
            beerDB.setImageUrl(beerUser.getImageUrl());
            beerDB.setUserId(beerUser.getUserId());
            beersDB.add(beerDB);
        }
        return beersDB;
    }

    @GetMapping
    public List<BeerDB> queryBeersDecomposition() {
        QueryBeersRequest beersRequest = QueryBeersRequest.newBuilder().build();
        return convertBeerQueryResponseToBeerDB(blockingStub.queryBeersDecomposition(beersRequest).next().getBeersList());


    }

    @GetMapping("/{id}")
    public BeerDB queryBeerByIdDecomposition(@PathVariable String id) {
        QueryBeerByIdRequest beersRequest = QueryBeerByIdRequest.newBuilder().setBeerId(id).build();
        BeerQueryResponse response = blockingStub.queryBeerByIdDecomposition(beersRequest).getBeer();
        return new BeerDB(response.getId(), response.getName(), response.getDescription(), convertIngredientQueryResponseToDB(response.getIngredientsList()), response.getMethodsList().toArray(new String[0]), response.getImageUrl(), response.getUserId());
    }



}

