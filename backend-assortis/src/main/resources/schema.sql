-- Create sequences that are manually referenced in entities but not managed by Hibernate
CREATE SEQUENCE IF NOT EXISTS confirmed_org_matches_id_seq;
CREATE SEQUENCE IF NOT EXISTS cv_evaluation_scores_id_seq;
CREATE SEQUENCE IF NOT EXISTS early_intelligence_id_seq;
CREATE SEQUENCE IF NOT EXISTS email_queue_id_seq;
CREATE SEQUENCE IF NOT EXISTS expert_credit_packs_id_seq;
CREATE SEQUENCE IF NOT EXISTS expert_cv_broadcasts_id_seq;
CREATE SEQUENCE IF NOT EXISTS expert_cv_downloads_id_seq;
CREATE SEQUENCE IF NOT EXISTS expert_fee_stats_id_seq;
CREATE SEQUENCE IF NOT EXISTS expert_interest_matches_id_seq;
CREATE SEQUENCE IF NOT EXISTS org_interest_matches_id_seq;
CREATE SEQUENCE IF NOT EXISTS procurement_types_id_seq;
CREATE SEQUENCE IF NOT EXISTS profile_change_requests_id_seq;
CREATE SEQUENCE IF NOT EXISTS project_documents_uploads_id_seq;
CREATE SEQUENCE IF NOT EXISTS project_extracted_keywords_id_seq;
CREATE SEQUENCE IF NOT EXISTS scheduled_notification_jobs_id_seq;
