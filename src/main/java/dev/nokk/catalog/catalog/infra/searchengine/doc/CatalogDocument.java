package dev.nokk.catalog.catalog.infra.searchengine.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "catalog")
public class CatalogDocument {
    @Id
    private UUID id;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String name;
}
