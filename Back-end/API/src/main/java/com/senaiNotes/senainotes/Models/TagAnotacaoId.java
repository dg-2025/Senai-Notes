package com.senaiNotes.senainotes.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TagAnotacaoId implements Serializable {
    private static final long serialVersionUID = -383413714599032078L;
    @Column(name = "id_tag", nullable = false)
    private Integer idTag;

    @Column(name = "id_nota", nullable = false)
    private Integer idNota;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TagAnotacaoId entity = (TagAnotacaoId) o;
        return Objects.equals(this.idTag, entity.idTag) &&
                Objects.equals(this.idNota, entity.idNota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTag, idNota);
    }

}