CREATE TABLE tenants (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE plans (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       price DECIMAL(10,2) NOT NULL,
                       billing_cycle VARCHAR(20) NOT NULL,
                       active BOOLEAN DEFAULT TRUE
);

CREATE TABLE subscriptions (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               tenant_id BIGINT NOT NULL,
                               plan_id BIGINT NOT NULL,
                               status VARCHAR(20) NOT NULL,
                               start_date DATE NOT NULL,
                               next_billing_date DATE NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_sub_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id),
                               CONSTRAINT fk_sub_plan FOREIGN KEY (plan_id) REFERENCES plans(id)
);

CREATE TABLE invoices (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          tenant_id BIGINT NOT NULL,
                          subscription_id BIGINT NOT NULL,
                          amount DECIMAL(10,2) NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          due_date DATE NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_inv_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id),
                          CONSTRAINT fk_inv_sub FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
);

CREATE TABLE payments (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          tenant_id BIGINT NOT NULL,
                          invoice_id BIGINT NOT NULL,
                          amount DECIMAL(10,2) NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          idempotency_key VARCHAR(255) UNIQUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_pay_inv FOREIGN KEY (invoice_id) REFERENCES invoices(id)
);
