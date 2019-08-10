package com.maxcheung.camelsimple.stuff;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping(value = "/stuff")
class StuffController {

    private StuffService stuffService;
    
    @Autowired
    StuffController(StuffService stuffService) {
        this.stuffService = stuffService;
    }
    
    @RequestMapping(value = "mimic", method = GET, produces="application/json")
    @ResponseStatus(OK)
    public ResponseEntity<Stuff> mimic() throws StuffException {
     //   public ResponseEntity<Stuff> mimic( @RequestParam("s") String str, @ModelAttribute("user") User user) throws StuffException {
     
        
        String val = "test";
        return new ResponseEntity<>(new Stuff(val), OK);
    }
}
