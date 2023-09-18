package com.example.webscrapingpasaportes.services;

import com.example.webscrapingpasaportes.models.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ScraperServiceImpl {


    public Set<ResponseDTO> getVehicleByModel(String vehicleModel){
        //Using a set here to only store unique elements
        Set<ResponseDTO> responseDTOS = new HashSet<>();
        extractDataFromRiyasewana(responseDTOS,"https://riyasewana.com/search/" + vehicleModel);

        System.out.println(responseDTOS.toString());
        return responseDTOS;
    }

    private void extractDataFromRiyasewana(Set<ResponseDTO> responseDTOS, String url) {

        try {
            //loading the HTML to a Document Object
            Document document = Jsoup.connect(url).get();
            //Selecting the element which contains the ad list
            Element element = document.getElementById("content");
            //getting all the <a> tag elements inside the content div tag
            Elements elements = element.getElementsByTag("a");
            //traversing through the elements
            for (Element ads: elements) {
                ResponseDTO responseDTO = new ResponseDTO();

                if (!StringUtils.isEmpty(ads.attr("title")) ) {
                    //mapping data to the model class
                    responseDTO.setTitle(ads.attr("title"));
                    responseDTO.setUrl(ads.attr("href"));
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void consultarBogota(){
        try{
            Document doc = Jsoup.connect("https://www.cancilleria.gov.co/tramites_servicios/pasaportes").get();
            Element element = doc.getElementById("block-bootstrap-barrio-subtheme-cancilleria-block-111");
            Elements elements = element.getElementsByClass("content");
            Element contenedorp = elements.get(0).getElementsByClass("clearfix").get(0);
            Elements elementosP = contenedorp.getElementsByTag("p");
            Element elementoPLink = elementosP.get(4);
            Element elementoLink = elementoPLink.getElementsByTag("a").get(0);
            String url = elementoLink.attr("href");
            System.out.println(url);
            System.out.println("hola");
        } catch (IOException e){
            e.printStackTrace();
        }

    }


}
