package net.maxqfz.superkassa;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class JsonHelper {
    private Gson gson = new GsonBuilder().serializeNulls().create();

    private String readString(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return reader.nextString();
    }

    public List<Flight> readData(String file) {
        List<Flight> flights = new LinkedList<Flight>();
        try {
            JsonReader reader = gson.newJsonReader(new FileReader(file));
            reader.beginArray();
            while(reader.hasNext()) {
                reader.beginArray();
                flights.add(new Flight(
                        readString(reader),
                        readString(reader),
                        readString(reader),
                        readString(reader)
                ));
                reader.endArray();
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public void writeData(String file, List<Flight> flights) {
        try {
            JsonWriter writer = gson.newJsonWriter(new FileWriter(file));
            writer.beginArray();
            for (Flight flight:flights)
                writer.beginArray()
                        .value(flight.getE1())
                        .value(flight.getE2())
                        .value(flight.getE3())
                        .value(flight.getE4())
                        .endArray();
            writer.endArray();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}