package id.natanhp.moviesservice.util;

import lombok.Getter;
import org.modelmapper.ModelMapper;

public final class ModelMapperHelper {
    private static volatile ModelMapperHelper instance;

    @Getter
    private final ModelMapper modelMapper;

    private ModelMapperHelper() {
        modelMapper = new ModelMapper();
    }

    public static ModelMapperHelper getInstance() {
        ModelMapperHelper result = instance;

        if (result != null) {
            return result;
        }

        synchronized (ModelMapperHelper.class) {
            if (instance == null) {
                instance = new ModelMapperHelper();
            }

            return instance;
        }
    }
}
