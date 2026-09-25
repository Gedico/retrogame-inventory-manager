-- =============================================================================
-- INVENTORY MANAGER DATABASE SCHEMA
-- Database: PostgreSQL
-- Description: Full schema creation including constraints and seed data
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. USERS TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER' CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN')),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------------------------------
-- 2. STATUS CODES TABLE & SEED DATA
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS status_codes (
    id SERIAL PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    description VARCHAR(100)
);

-- Popolamento degli stati della pipeline di vendita
INSERT INTO status_codes (code, description) VALUES 
    ('ACQUISTATO', 'Oggetto acquistato ma non ancora lavorato'),
    ('FOTOGRAFATO', 'Oggetto testato, pulito e fotografato'),
    ('PUBBLICATO', 'Oggetto online sui marketplace'),
    ('VENDUTO_PROFITTO', 'Venduto con utile netto positivo'),
    ('VENDUTO_PAREGGIO', 'Venduto in pareggio costi'),
    ('VENDUTO_PERDITA', 'Venduto in perdita')
ON CONFLICT (code) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 3. LOTS TABLE (Contenitore economico per acquisti in blocco)
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS lots (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    
    title VARCHAR(150) NOT NULL,
    purchase_price NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    provenance VARCHAR(50) NOT NULL,
    notes TEXT,
    
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------------------------------
-- 4. INVENTORY ITEMS TABLE (Specifiche fisiche dell'oggetto)
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS inventory_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status_id INT NOT NULL REFERENCES status_codes(id),
    lot_id BIGINT REFERENCES lots(id) ON DELETE SET NULL, -- NULL se oggetto singolo
    
    title VARCHAR(150) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    condition_type VARCHAR(20) NOT NULL CHECK (
        condition_type IN ('PESSIMO', 'ACCETTABILE', 'BUONO', 'OTTIMO', 'ECCELLENTE', 'NUOVO')
    ),
    notes TEXT,
    
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------------------------------
-- 5. ITEM ECONOMICS TABLE (Dati finanziari e tracciamento temporale)
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS item_economics (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL UNIQUE REFERENCES inventory_items(id) ON DELETE CASCADE,
    
    provenance VARCHAR(50),
    purchase_price NUMERIC(10, 2) DEFAULT 0.00,
    estimated_sell_price NUMERIC(10, 2),
    
    sale_price NUMERIC(10, 2),
    platform_fees NUMERIC(10, 2) DEFAULT 0.00,
    shipping_cost NUMERIC(10, 2) DEFAULT 0.00,
    net_profit NUMERIC(10, 2),
    
    published_at TIMESTAMP WITH TIME ZONE,
    sold_at TIMESTAMP WITH TIME ZONE
);
