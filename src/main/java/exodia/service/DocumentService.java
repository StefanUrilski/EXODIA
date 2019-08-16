package exodia.service;

import exodia.domain.model.service.DocumentServiceModel;

public interface DocumentService {

    DocumentServiceModel saveSchedule(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findByDocumentId(String id);
}
