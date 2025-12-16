-- Nettoyage de la table (optionnel)
DELETE FROM product;

-- Insertion des produits de démonstration
INSERT INTO product (titre, description, image, prix) VALUES
                                                          ('Vase antique', 'Vase ayant appartenu à Zeus. Pièce rare du 5ème siècle avant J.C. En excellent état de conservation.', '/images/vase.jpg', 730.0),
                                                          ('Table ancienne', 'Table en chêne massif du 18ème siècle. Style Louis XV avec sculptures raffinées. Parfait état.', '/images/table.jpg', 1200.0),
                                                          ('Tableau impressionniste', 'Peinture à l''huile du 19ème siècle, école impressionniste. Signée par l''artiste.', '/images/tableau.jpg', 850.0),
                                                          ('Horloge comtoise', 'Horloge comtoise du 19ème siècle. Mécanisme d''origine restauré. Sonnerie fonctionnelle.', '/images/horloge.jpg', 650.0),
                                                          ('Secrétaire Empire', 'Secrétaire style Empire en acajou. Marquetterie fine et bronzes dorés. Début 19ème.', '/images/secretaire.jpg', 1850.0),
                                                          ('Service porcelaine', 'Service complet de porcelaine de Limoges, 42 pièces. Décor bleu et or. Années 1920.', '/images/porcelaine.jpg', 950.0),
                                                          ('Lustre cristal', 'Lustre en cristal de Bohême, 12 bras de lumière. Diamètre 80cm. Années 1930.', '/images/lustre.jpg', 1500.0),
                                                          ('Fauteuil club', 'Fauteuil club en cuir patiné. Années 1950. Confort exceptionnel. Cuir d''origine.', '/images/fauteuil.jpg', 750.0),
                                                          ('Bibliothèque', 'Bibliothèque en chêne massif. Style Art Déco. Années 1930. 2m de hauteur.', '/images/bibliotheque.jpg', 2200.0),
                                                          ('Bureau ministre', 'Bureau style ministre en noyer. 19ème siècle. Tiroirs fonctionnels. Plateau marqueté.', '/images/bureau.jpg', 2800.0);