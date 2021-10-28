package id.natanhp.data.endpoint;

import id.natanhp.data.entity.Movies;
import id.natanhp.data.service.MoviesService;

import java.util.List;
import java.util.Optional;

import com.vaadin.fusion.Endpoint;

import org.vaadin.artur.helpers.GridSorter;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.PagingUtil;

import com.vaadin.fusion.Nonnull;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Endpoint
@AnonymousAllowed
public class MoviesEndpoint {

    private MoviesService service;

    public MoviesEndpoint(@Autowired MoviesService service) {
        this.service = service;
    }

    @Nonnull
    public List<@Nonnull Movies> list(int offset, int limit, @Nonnull List<@Nonnull GridSorter> sortOrder) {
        Page<Movies> page = service
                .list(PagingUtil.offsetLimitTypeScriptSortOrdersToPageable(offset, limit, sortOrder));
        return page.getContent();
    }

    public Optional<Movies> get(@Nonnull Integer id) {
        return service.get(id);
    }

    @Nonnull
    public Movies update(@Nonnull Movies entity) {
        return service.update(entity);
    }

    public void delete(@Nonnull Integer id) {
        service.delete(id);
    }

    public int count() {
        return service.count();
    }

}
