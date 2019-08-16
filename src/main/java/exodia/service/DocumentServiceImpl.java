package exodia.service;

import exodia.domain.entity.Document;
import exodia.domain.entity.User;
import exodia.domain.model.service.DocumentServiceModel;
import exodia.repository.DocumentRepository;
import exodia.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(ModelMapper modelMapper,
                               UserRepository userRepository,
                               DocumentRepository documentRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentServiceModel saveSchedule(DocumentServiceModel documentServiceModel) {
        try {
            Document document = modelMapper.map(documentServiceModel, Document.class);
            document.setUser(null);

            User user = userRepository.findById(documentServiceModel.getUserId()).orElse(null);
            if (user == null) { return null; }

            documentRepository.save(document);
            document.setUser(user);
            documentRepository.saveAndFlush(document);

            return modelMapper.map(document, DocumentServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DocumentServiceModel findByDocumentId(String id) {
        Document document = documentRepository.findById(id).orElse(null);

        return (document == null)
                ? null
                : modelMapper.map(document, DocumentServiceModel.class);
    }

    @Override
    public List<DocumentServiceModel> findAllDocumentsByUserId(String userId) {
        return documentRepository.findAllByUserId(userId).stream()
                .map(document -> modelMapper.map(document, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteByDocumentId(String id) {
        try{
            documentRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
