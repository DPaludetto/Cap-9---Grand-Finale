package br.com.palutec.claro.pagadoria.search;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Transactional
@Component
public class Indexer {

	@Autowired
    private EntityManager entityManager;

    private static final int THREAD_NUMBER = 4;

    public Indexer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void indexPersistedData(Class<?> classToIndex) throws IndexException {

        try {
        	
            SearchSession searchSession = Search.session(entityManager);

//            Class<?> classToIndex = Class.forName(indexClassName);
            MassIndexer indexer =
                    searchSession
                            .massIndexer(classToIndex)
                            .threadsToLoadObjects(THREAD_NUMBER);

            indexer.startAndWait();
        } catch (InterruptedException e) {
            throw new IndexException(e, "Index Interrupted");
        }
    }
}
