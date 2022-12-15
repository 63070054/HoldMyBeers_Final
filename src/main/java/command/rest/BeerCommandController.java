package command.rest;

import service.BeerServiceImpl;

public class BeerCommandController {
    private final BeerServiceImpl beerService;

    public BeerCommandController(BeerServiceImpl beerService) {
        this.beerService = beerService;
    }



}
