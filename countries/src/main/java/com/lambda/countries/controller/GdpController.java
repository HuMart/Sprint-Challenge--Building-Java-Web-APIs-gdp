package com.lambda.countries.controller;

import com.lambda.countries.GdpApplication;
import com.lambda.countries.GdpList;
import com.lambda.countries.exception.ResourceNotFoundException;
import com.lambda.countries.model.GDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/economies")
public class GdpController
{
    private static final Logger logger = LoggerFactory.getLogger(GdpController.class);

    @GetMapping(value = "/names")
    public ResponseEntity<?> getByName()
    {
        logger.info("/names  accessed");
        GdpList rtnGdpList = GdpApplication.ourGdpList;
        rtnGdpList.gdpList.sort((g1, g2) -> g1.getCname().compareToIgnoreCase(g2.getCname()));
        return new ResponseEntity<>(rtnGdpList, HttpStatus.OK);
    }

    @GetMapping(value = "/economy")
    public ResponseEntity<?> getByGDP()
    {
        logger.info("/economy  accessed");
        GdpList rtnGdpList = GdpApplication.ourGdpList;
        rtnGdpList.gdpList.sort((g1, g2) -> (int) (g2.getGdp() - g1.getGdp()));
        return new ResponseEntity<>(rtnGdpList, HttpStatus.OK);
    }

    @GetMapping(value = "/country/{id}")
    public ResponseEntity<?> getEconomyDetail(
            @PathVariable
                    long id)
    {
        logger.info("/country/" + id + " was accessed");
        GDP rtnGdp;
        if (GdpApplication.ourGdpList.findGdp(g -> (g.getId() == id)) == null) {
            throw new ResourceNotFoundException("Economy with ID " + id + " not found.");
        } else {
            rtnGdp = GdpApplication.ourGdpList.findGdp(g -> (g.getId() == id));
        }
        return new ResponseEntity<>(rtnGdp, HttpStatus.OK);
    }

    @GetMapping(value = "/country/stats/median",
                produces = {"application/json"})
    public ResponseEntity<?> getMedianGdp()
    {
        logger.info("/country/stats/median was accessed");
        return new ResponseEntity<>(GdpApplication.ourGdpList.findMedianGdp(), HttpStatus.OK);
    }

    @GetMapping(value = "/total",
                produces = {"application/json"})
    public ResponseEntity<?> getTotal() {
        logger.info("/total, accessed");
        return new ResponseEntity<>(GdpApplication.ourGdpList.getTotal(), HttpStatus.OK);
    }

    @GetMapping(value = "/gdp/list/{start}/{end}",
                produces = {"application/json"})
    public ResponseEntity<?> getLimitedGdpList(
            @PathVariable
                    String start,
            @PathVariable
                    String end) {
        logger.info("/gdp/list/" + start + "/" + end + " was accessed");
        GdpList tmpGdpList = GdpApplication.ourGdpList;
        tmpGdpList.gdpList.sort((g1, g2) -> (int) (g2.getGdp() - g1.getGdp()));
        ArrayList<GDP> rtnList = tmpGdpList.findEconomies(g -> {
            if (g.getGdp() >= Long.parseLong(start) && g.getGdp() <= Long.parseLong(end)) {
                return true;
            } else {
                return false;
            }
        });
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }


    @GetMapping(value = "/economy/table")
    public ModelAndView displayEconomiesTable()
    {
        ArrayList<GDP> rtnGdpList = GdpApplication.ourGdpList.gdpList;
        rtnGdpList.sort((g1, g2) -> (int) (g2.getGdp() - g1.getGdp()));
        logger.info("/economy/table was accessed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("countries");
        mav.addObject("gdpList", rtnGdpList);

        return mav;
    }
}
