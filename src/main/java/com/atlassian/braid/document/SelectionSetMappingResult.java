package com.atlassian.braid.document;

import com.atlassian.braid.document.FieldOperation.FieldOperationResult;
import com.atlassian.braid.mapper.MapperOperation;
import graphql.language.Field;
import graphql.language.SelectionSet;

import static com.atlassian.braid.document.FieldOperation.result;
import static com.atlassian.braid.document.Fields.cloneFieldWithNewSelectionSet;
import static com.atlassian.braid.document.Fields.getFieldAliasOrName;
import static com.atlassian.braid.mapper.MapperOperations.map;
import static com.atlassian.braid.mapper.Mappers.mapper;
import static java.util.Objects.requireNonNull;

/**
 * This is an intermediary object for mapping results
 */
final class SelectionSetMappingResult {
    private final SelectionSet selectionSet;
    private final MapperOperation resultMapper;

    SelectionSetMappingResult(SelectionSet selectionSet, MapperOperation resultMapper) {
        this.selectionSet = requireNonNull(selectionSet);
        this.resultMapper = requireNonNull(resultMapper);
    }

    SelectionSet getSelectionSet() {
        return selectionSet;
    }

    MapperOperation getResultMapper() {
        return resultMapper;
    }

    FieldOperationResult toFieldOperationResult(Field field) {
        return result(
                cloneFieldWithNewSelectionSet(field, selectionSet),
                map(getFieldAliasOrName(field), mapper(resultMapper)));
    }
}
