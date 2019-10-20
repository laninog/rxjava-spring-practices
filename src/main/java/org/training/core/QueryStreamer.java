package org.training.core;

import io.reactivex.Observable;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Criterion;

public class QueryStreamer<T> {

    private final SessionFactory factory;
    private final int fetchSize;
    private final Class<T> clazz;

    public QueryStreamer(SessionFactory factory, Class<T> what) {
        this(factory, what, 20);
    }

    public QueryStreamer(SessionFactory factory, Class<T> what, int fetchSize) {
        this.factory = factory;
        this.fetchSize = fetchSize;
        this.clazz = what;
    }

    public Observable<T> stream(Criterion criteria) {
        final StatelessSession session = factory.openStatelessSession();

        return Observable.create(emitter -> {
            try (ScrollableResults results = session.createCriteria(clazz.getClass())
                    .add(criteria)
                    .setReadOnly(true)
                    .setFetchSize(fetchSize)
                    .scroll(ScrollMode.FORWARD_ONLY)) {

                while (results.next()) {
                    T obj = clazz.cast(results.get(0));
                    emitter.onNext(obj);
                }
                emitter.onComplete();
            } catch (Exception ex) {
                emitter.onError(ex);
            } finally {
                session.close();
            }
        });

    }

}
