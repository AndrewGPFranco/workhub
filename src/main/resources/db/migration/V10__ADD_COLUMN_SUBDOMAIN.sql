ALTER TABLE daily ADD subdomain_id UUID;

ALTER TABLE demands ADD subdomain_id UUID;

ALTER TABLE feedbacks ADD subdomain_id UUID;

ALTER TABLE daily ADD CONSTRAINT FK_DAILY_ON_SUBDOMAIN FOREIGN KEY (subdomain_id) REFERENCES subdomains (id);

ALTER TABLE demands ADD CONSTRAINT FK_DEMANDS_ON_SUBDOMAIN FOREIGN KEY (subdomain_id) REFERENCES subdomains (id);

ALTER TABLE feedbacks ADD CONSTRAINT FK_FEEDBACKS_ON_SUBDOMAIN FOREIGN KEY (subdomain_id) REFERENCES subdomains (id);

CREATE INDEX idx_daily_user_subdomain ON daily (user_id, subdomain_id);

CREATE INDEX idx_demands_user_subdomain ON demands (user_id, subdomain_id);

CREATE INDEX idx_feedbacks_user_subdomain ON feedbacks (user_id, subdomain_id);
