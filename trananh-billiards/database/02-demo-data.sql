-- Optional local demo data. Run after 01-schema.sql; do not run on production.
-- Re-running this script does not duplicate these names or overwrite existing prices.
BEGIN;

INSERT INTO public.products (name, price)
SELECT demo.name, demo.price
FROM (VALUES
    ('Cơ bida mẫu A', 1200000::BIGINT),
    ('Cơ bida mẫu B', 2500000::BIGINT),
    ('Bao đựng cơ mẫu', 350000::BIGINT)
) AS demo(name, price)
WHERE NOT EXISTS (
    SELECT 1 FROM public.products existing WHERE existing.name = demo.name
);

COMMIT;
