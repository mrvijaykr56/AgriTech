-- src/main/resources/db/migration/V1__init.sql

CREATE TABLE farmers (
  farmer_id VARCHAR(64) PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  mobile VARCHAR(32),
  village VARCHAR(128),
  has_consent BOOLEAN NOT NULL DEFAULT FALSE,
  consent_timestamp BIGINT
);

CREATE TABLE fields (
  field_id VARCHAR(64) PRIMARY KEY,
  farmer_id VARCHAR(64) NOT NULL,
  crop VARCHAR(64) NOT NULL,
  area_sq_m INT,
  FOREIGN KEY (farmer_id) REFERENCES farmers(farmer_id) ON DELETE CASCADE
);

CREATE TABLE inspections (
  inspection_id VARCHAR(64) PRIMARY KEY,
  field_id VARCHAR(64) NOT NULL,
  commodity VARCHAR(32) NOT NULL,   -- litchi/makhana
  label VARCHAR(128) NOT NULL,
  confidence FLOAT NOT NULL,
  risk_level VARCHAR(32) NOT NULL,
  latitude DOUBLE,
  longitude DOUBLE,
  created_at BIGINT NOT NULL,
  FOREIGN KEY (field_id) REFERENCES fields(field_id) ON DELETE CASCADE
);

CREATE TABLE consent_logs (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  farmer_id VARCHAR(64) NOT NULL,
  consent_value BOOLEAN NOT NULL,
  recorded_at BIGINT NOT NULL,
  note VARCHAR(256),
  FOREIGN KEY (farmer_id) REFERENCES farmers(farmer_id) ON DELETE CASCADE
);

CREATE TABLE funding_milestones (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(64) UNIQUE NOT NULL,       -- e.g., PHASE1_SYNC_COMPLETE
  title VARCHAR(128) NOT NULL,
  description TEXT,
  due_date BIGINT,
  achieved BOOLEAN NOT NULL DEFAULT FALSE,
  achieved_at BIGINT,
  evidence_url VARCHAR(256)
);