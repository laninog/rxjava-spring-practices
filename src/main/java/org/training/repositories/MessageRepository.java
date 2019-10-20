package org.training.repositories;

import io.reactivex.Observable;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.training.core.QueryStreamer;
import org.training.domain.Message;

import javax.persistence.EntityManagerFactory;

@Repository
public class MessageRepository {

    @Autowired
    private EntityManagerFactory emFactory;

    public Observable<Message> findAll() {
        return new QueryStreamer<Message>(emFactory.unwrap(SessionFactory.class), Message.class)
                .stream(Restrictions.isNotNull("text"));
    }

}
