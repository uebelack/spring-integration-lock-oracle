CREATE TABLE INT_LOCK  (
   LOCK_KEY VARCHAR2(36) NOT NULL,
   REGION VARCHAR2(100) NOT NULL,
   CLIENT_ID VARCHAR2(36),
   CREATED_DATE TIMESTAMP NOT NULL,
   constraint INT_LOCK_PK primary key (LOCK_KEY, REGION)
);