package com.example.realtimeticketingsystem.service;

import com.example.realtimeticketingsystem.entity.ConfigurationEntity;
import com.example.realtimeticketingsystem.repository.ConfigurationRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ConfigurationService {
    private ConfigurationRepository repository;

    @Autowired
    public ConfigurationService(ConfigurationRepository repository) {
        this.repository = repository;
    }

    public ConfigurationEntity saveConfiguration(ConfigurationEntity configurations) throws IOException {

        File file = new File("TicketConfiguration.json");
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        String json = gson.toJson(configurations);
        FileWriter fw = new FileWriter(file);
        try {
            fw.write(json);
        }catch (IOException e){e.printStackTrace();}
        fw.close();
        System.out.println("Configurations Have Been Saved at: "+file);

        return repository.save(configurations);

    }

}
