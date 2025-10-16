package com.senaiNotes.senainotes.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tag_anotacao", schema = "notes")
public class TagAnotacao {
    @EmbeddedId
    private TagAnotacaoId id;

    @MapsId("idTag")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_tag", nullable = false)
    private Tag idTag;

    @MapsId("idNota")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_nota", nullable = false)
    private Nota idNota;

}