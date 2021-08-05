CREATE KEYSPACE IF NOT EXISTS test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
DROP TABLE IF EXISTS test.stockevents ;
CREATE TABLE test.stockevents (
  entityId  ASCII,
  version   INT,
  eventType ASCII,
  opId      ASCII,
  opDate    TIMESTAMP,
  status    ASCII,
  auditinfo ASCII,
  eventData VARCHAR,
  PRIMARY KEY (entityId, version)
);

CREATE MATERIALIZED VIEW test.stockevents_byOps AS
  SELECT
    opId,
    entityId,
    version,
    eventType,
    opDate,
    status,
    auditinfo,
    eventData
  FROM test.stockevents
  WHERE opId IS NOT NULL AND entityId IS NOT NULL AND version IS NOT NULL
  PRIMARY KEY (opid, entityId, VERSION );
