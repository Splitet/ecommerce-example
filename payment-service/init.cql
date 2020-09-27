CREATE KEYSPACE IF NOT EXISTS eventapis WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
DROP TABLE IF EXISTS eventapis.paymentevents ;
CREATE TABLE eventapis.paymentevents (
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

CREATE MATERIALIZED VIEW eventapis.paymentevents_byOps AS
  SELECT
    opId,
    entityId,
    version,
    eventType,
    opDate,
    status,
    auditinfo,
    eventData
  FROM eventapis.paymentevents
  WHERE opId IS NOT NULL AND entityId IS NOT NULL AND version IS NOT NULL
  PRIMARY KEY (opid, entityId, VERSION );
