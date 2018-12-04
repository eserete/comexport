package com.example.comexport.accountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class AccountantEntryRepositoryImpl implements AccountantEntryRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AccountantEntryRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Stats stats(Integer accountNumber) {
        List<AggregationOperation> operations = new ArrayList<>();

        Optional.ofNullable(accountNumber)
                .map(accNumber -> match(Criteria.where("accountNumber").is(accNumber)))
                .ifPresent(operations::add);

        operations.add(group()
                .sum("amount").as("sum")
                .avg("amount").as("average")
                .min("amount").as("min")
                .max("amount").as("max")
                .count().as("count"));


        Aggregation agg = newAggregation(operations);

        return mongoTemplate.aggregate(agg, AccountantEntry.class, Stats.class).getUniqueMappedResult();
    }
}
