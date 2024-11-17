package com.example.realtimeticketingsystem.service;

import com.example.realtimeticketingsystem.entity.ConfigurationEntity;
import com.example.realtimeticketingsystem.repository.ConfigurationRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

@Service
public class ConfigurationService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    // For testing purposes using logger
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

    public List<String> retrieveEventNames(){
        return repository.getAllEventNamesOrderByIdAsc();
    }
    public List<Integer> retrieveNoOfTickets(){
        return repository.getAllNoOfTicketsOrderByIdAsc();
    }
    public List<Double> retrieveTicketRates(){
        return repository.getAllTicketRateOrderByIdAsc();
    }
    public void readFile() {
        File file = new File("TicketConfiguration.json");
        if (file.isFile()){
           logger.info("Tickets Configurations Exist");
        }
        if (file.canRead()){
            logger.info("Readable");
        }
        try (FileReader fr = new FileReader(file)) {
            int character;
            while ((character = fr.read()) != -1) {  // Read one character at a time
                System.out.print((char) character);  // Print each character
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file.getPath(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getPath(), e);
        }
    }





}
