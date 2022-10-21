package com.epam.esm.link;

import org.springframework.hateoas.RepresentationModel;

public interface AddLink <T extends RepresentationModel<T>>{

    void addLinks(T t);

    void pageLink(int page, int size, T t);
}
