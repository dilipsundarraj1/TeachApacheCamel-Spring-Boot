package com.learncamel.processor;

import com.learncamel.domain.Item;
import com.learncamel.exception.DataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Created by z001qgd on 1/17/18.
 */
@Component
@Slf4j
public class BuildSQLProcessor implements org.apache.camel.Processor {

    String tableName ="ITEMS";
    @Override
    public void process(Exchange exchange) throws Exception {
        Item item = (Item) exchange.getIn().getBody();

        System.out.println("Inside Processor : " + item);
        StringBuilder query = new StringBuilder();
        if(ObjectUtils.isEmpty(item.getSku())){
            throw new DataException("Sku is null for " + item.getItemDescription());
        }
        if(item.getTransactionType().equals("ADD")){
            query.append("INSERT INTO "+tableName+" (SKU, ITEM_DESCRIPTION,PRICE) VALUES ('");
            query.append(item.getSku()+"','" + item.getItemDescription()+"',"+ item.getPrice()+" );");
        }else if(item.getTransactionType().equals("UPDATE")){
            query.append("UPDATE "+tableName+"  SET PRICE =");
            query.append(item.getPrice()+" where SKU ='"+item.getSku()+"'");
        }else if(item.getTransactionType().equals("DELETE")){
            query.append("DELETE FROM "+tableName+"  WHERE SKU ='"+item.getSku()+"'");
        }
        log.info("Query is : " + query);

        exchange.getIn().setBody(query.toString());
    }
}
