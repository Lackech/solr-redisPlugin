package com.kevinjimenez.maven;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RedisQueryParserPlugin extends QParserPlugin{

    private static final Logger log = LoggerFactory.getLogger(RedisQueryParserPlugin.class);

    @Override
    public void init(NamedList args) {
        super.init(args);
        try{
            String text = args.toString();
            Files.write(Paths.get("/home/kevin/fileName1.txt"), text.getBytes());
        }catch (Exception e){

        }

        final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt((String) args.get("maxConnections")));

        final String host = (String) args.get("host");
        final int database = Integer.parseInt((String) args.get("database"));
        final int port = Integer.parseInt((String) args.get("port"));

        log.info("Initialized RedisQParserPlugin with host: " + host);
    }

    @Override
    public QParser createParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        return new RedisQueryParser(qstr, localParams, params, req);
    }
}
