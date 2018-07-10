package cl.vicentepc.flashg7.views.main.finder;

public interface FinderCallback {

    void error(String error);

    void success();

    void notFound();

}
