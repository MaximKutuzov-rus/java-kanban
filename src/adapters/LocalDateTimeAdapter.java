package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.value("null");
        } else {
            jsonWriter.value(localDateTime.format(dtf));
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        String localDateTime = jsonReader.nextString();
        if (localDateTime.equals("null")) {
            return null;
        }
        return LocalDateTime.parse(localDateTime, dtf);
    }
}