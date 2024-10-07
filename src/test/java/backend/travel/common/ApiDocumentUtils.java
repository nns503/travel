package backend.travel.common;

import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public interface ApiDocumentUtils {

    static OperationResponsePreprocessor getDocumentResponse(){
        return preprocessResponse(prettyPrint());
    }
}
