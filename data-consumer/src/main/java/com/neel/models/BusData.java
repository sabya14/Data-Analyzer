package com.neel.models;

import org.apache.solr.client.solrj.beans.Field;

import java.util.UUID;


public class BusData {

    private UUID id;
    private String publishedLineName;
    private String originName;
    private String destinationName;
    private String VehicleRef;

    @Field("id")
    void setId(UUID id) {
        this.id = id;
    }

    @Field("publishedLineName")
    void setPublishedLineName(String publishedLineName) {
        this.publishedLineName = publishedLineName;
    }

    @Field("originName")
    void setOriginName(String originName) {
        this.originName = originName;
    }

    @Field("destinationName")
    void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }


    @Field("vehicleRef")

    void setVehicleRef(String vehicleRef) {
        VehicleRef = vehicleRef;
    }
}
