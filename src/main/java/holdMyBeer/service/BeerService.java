package holdMyBeer.service;

import com.proto.prime.*;
import io.grpc.stub.StreamObserver;

public class BeerService extends BeerServiceGrpc.BeerServiceImplBase {
    @Override
    public void createBeerDecomposition(CreateBeerRequest request, StreamObserver<CreateBeerResponse> responseObserver) {

    }

    @Override
    public void signInDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        super.signInDecomposition(request, responseObserver);
    }

    @Override
    public void queryBeersDecomposition(QueryBeersRequest request, StreamObserver<QueryBeerByIdResponse> responseObserver) {
        super.queryBeersDecomposition(request, responseObserver);
    }

    @Override
    public void addBeerToFavorite(AddBeerToFavoriteRequest request, StreamObserver<AddBeerToFavoriteResponse> responseObserver) {
        super.addBeerToFavorite(request, responseObserver);
    }

    @Override
    public void removeBeerToFavorite(RemoveBeerToFavoriteRequest request, StreamObserver<RemoveBeerToFavoriteResponse> responseObserver) {
        super.removeBeerToFavorite(request, responseObserver);
    }

    @Override
    public void queryBeerByIdDecomposition(QueryBeerByIdRequest request, StreamObserver<QueryBeerByIdResponse> responseObserver) {

    }

    @Override
    public void editBeerDecomposition(EditBeerRequest request, StreamObserver<EditBeerResponse> responseObserver) {

    }

    @Override
    public void deleteBeerDecomposition(DeleteBeerRequest request, StreamObserver<DeleteBeerResponse> responseObserver) {

    }
}
