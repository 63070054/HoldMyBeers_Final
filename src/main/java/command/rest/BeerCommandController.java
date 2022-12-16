package command.rest;

import service.BeerService;

public class BeerCommandController {
    private final BeerService beerService;

    public BeerCommandController(BeerService beerService) {
        this.beerService = beerService;
    }



}
