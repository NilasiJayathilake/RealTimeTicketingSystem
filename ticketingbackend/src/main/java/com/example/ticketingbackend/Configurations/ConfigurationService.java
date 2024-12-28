package com.example.ticketingbackend.Configurations;

import com.example.ticketingbackend.Configurations.Configuration;
import com.example.ticketingbackend.Logger.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Getter
@Setter
/*For easy calling from other controllers or services as needed.
    Setting Getter and Setter for configuration service  */
// Didn't use gson as jackson is pre-installed and works fine with Springboot
public class ConfigurationService {

    Configuration configuration;
    @Autowired
    public ConfigurationService(Configuration configuration) {
        this.configuration = configuration;
    }

    public String saveConfiguration() throws IOException {
        File file = new File("TicketConfiguration.json");
        ObjectMapper objectMapper = new ObjectMapper();
        FileWriter fw = new FileWriter(file);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fw, configuration);
        } catch (IOException e) {
            Logger.faultLog(Thread.currentThread().getName(), "Couldn't save the configuration at "+file);
            throw new RuntimeException("Couldn't save the configuration at "+file);
        }
        fw.close();
        return "Configurations Have Been Saved at: " + file;
    }

    public Configuration loadConfiguration() throws IOException{
        File file = new File("TicketConfiguration.json");
        ObjectMapper objectMapper = new ObjectMapper();

//        if (file.exists()){
//            System.out.println("Configurations Exist: Retrieval Successful");
//            //to make sure gson knows what is the type of the class it should deserialize to passing Configurations.class
//        }else {
//            System.out.println("File Does Not Exist");
//        }

        try (FileReader reader = new FileReader(file)) {
            Configuration config = objectMapper.readValue(reader, Configuration.class);
            return config;
        } catch (Exception e) {
            Logger.faultLog(Thread.currentThread().getName(), "Couldn't load the configuration" +e.getMessage());
            throw new RuntimeException("Error during loading the Configuration: " + e.getMessage());
        }

    }
}
