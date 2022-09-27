# Email Table
CREATE TABLE Email(

        id BIGINT primary key generated always as identity,
        
        recipient_name varchar(100) not null,
        
        email_subject varchar(100) not null,
        
        email_body varchar(100),
        
        delivery_date timestamp DEFAULT CURRENT_TIMESTAMP,
        
        is_sent boolean
)
