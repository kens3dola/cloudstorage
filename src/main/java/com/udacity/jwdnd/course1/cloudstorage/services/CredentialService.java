package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> findAllByUserid(int userid){
        return this.credentialMapper.findAllByUserid(userid);
    }

    public Credential findById(int id){
        Credential cred = this.credentialMapper.findById(id);
        cred.setPassword(encryptionService.decryptValue(cred.getPassword(), cred.getKey()));
        return cred;
    }

    public int save(Credential credential){
        String hashedSalt = EncryptionService.generateSaltKey();
        credential.setKey(hashedSalt);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return this.credentialMapper.save(credential);
    }

    public void delete(int id){
        this.credentialMapper.delete(id);
    }

    public void updateCredential(Credential credential) {
        String hashedSalt = EncryptionService.generateSaltKey();
        credential.setKey(hashedSalt);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        this.credentialMapper.merge(credential);
    }
}
