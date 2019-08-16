package exodia.service;

import exodia.domain.model.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    DocumentServiceModel saveSchedule(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findByDocumentId(String id);

    List<DocumentServiceModel> findAllDocumentsByUserId(String userId);

    boolean deleteByDocumentId(String id);
}
