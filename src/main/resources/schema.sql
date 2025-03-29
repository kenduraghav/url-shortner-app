CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,           -- Auto-incrementing primary key
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,          -- Add role column
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- Add created_at column with default value
);


CREATE TABLE short_urls (
    id BIGSERIAL PRIMARY KEY,               -- Auto-incrementing primary key
    short_key VARCHAR(255) NOT NULL UNIQUE,   -- Short URL key
    original_url TEXT NOT NULL,              -- Original URL
    is_private BOOLEAN NOT NULL DEFAULT FALSE,           -- Click count for the short URL
    expires_at TIMESTAMP,                    -- Expiration time for the short URL
    created_by BIGINT,                       -- Reference to User table (user ID)
    click_count INT DEFAULT 0,  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Creation time
    CONSTRAINT fk_user FOREIGN KEY (created_by) REFERENCES users(id)  -- Foreign key constraint to 'users' table
);
