package com.example.jpa_learn.service.base;

import com.example.jpa_learn.repository.BaseRepository;
import com.example.jpa_learn.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractCrudService<Domain, ID> implements CrudService<Domain, ID> {
    private final BaseRepository<Domain, ID> repository;

    protected AbstractCrudService(BaseRepository<Domain, ID> repository) {
        this.repository = repository;
    }

    @Override
    public Domain create(Domain domain) {
        Assert.notNull(domain, " data must not be null");

        return repository.save(domain);
    }
    @Override
    public List<Domain> listAllByIds(Collection<ID> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : repository.findAllById(ids);
    }
    @Override
    public List<Domain> listAll() {
        return repository.findAll();
    }

    @Override
    public Domain getById(ID id){
        return fetchById(id).orElseThrow();
    }

    @Override
    public Optional<Domain> fetchById(ID id){
        Assert.notNull(id,  " id must not be null");
        return repository.findById(id);
    }

    @Override
    public Domain update(Domain domain){
        Assert.notNull(domain, " data must not be null");

        return repository.saveAndFlush(domain);
    }

    @Override
    public Domain removeById(ID id){

        Domain domain = getById(id);
        remove(domain);
        return domain;
    }

    @Override
    public void remove(Domain domain) {
        Assert.notNull(domain,  " data must not be null");

        repository.delete(domain);
    }

    @Override
    public void removeAll(Collection<Domain> domains) {
        if (CollectionUtils.isEmpty(domains)) {
            log.debug(" collection is empty");
            return;
        }
        repository.deleteInBatch(domains);
    }

    @Override
    public List<Domain> createInBatch(Collection<Domain> domains) {
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() :
                repository.saveAll(domains);
    }



}
