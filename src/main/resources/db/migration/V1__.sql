CREATE TABLE neurotech_client
(
    id   VARCHAR(255) NOT NULL,
    name VARCHAR(255) NULL,
    age  INT NULL,
    income DOUBLE NULL,
    CONSTRAINT pk_neurotechclient PRIMARY KEY (id)
);