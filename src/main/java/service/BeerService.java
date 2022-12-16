package service;

import com.proto.prime.*;
import io.grpc.stub.StreamObserver;

public class BeerService extends BeerServiceGrpc.BeerServiceImplBase {
    @Override
    public void createBeerDecomposition(CreateBeerRequest request, StreamObserver<CreateBeerResponse> responseObserver) {

    }

    @Override
    public void queryBeerByIdDecomposition(QueryBeerByIdRequest request, StreamObserver<QueryBeerByIdResponse> responseObserver) {

    }

    @Override
    public void editBeerDecomposition(EditBeerRequest request, StreamObserver<EditBeerResponse> responseObserver) {
        super.editBeerDecomposition(request, responseObserver);
    }

    @Override
    public void deleteBeerDecomposition(DeleteBeerRequest request, StreamObserver<DeleteBeerResponse> responseObserver) {
        super.deleteBeerDecomposition(request, responseObserver);
    }
}
