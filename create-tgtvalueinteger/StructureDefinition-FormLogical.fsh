Logical: FormLogical
Parent: Base
Characteristics: #can-be-target
Title: "Formulaires - modèle logique"
Description:  """Modèle logique pour les formulaires"""

* instance 1..* BackboneElement "instance"
  * subform 1..1 integer "subform" "identifiant du formulaire"
  * num 1..1 integer "num" "identifiant de l'instance de formulaire"
  * visit 1..1 integer "visit" "Identifiant du NDA"
* subform 1..* BackboneElement "subform"
  * num 1..1 integer "num" "identifiant du formulaire"
  * name 1..1 string "name" "Code du formulaire"
* field 0..* BackboneElement "field"
  * num 1..1 integer "num"
  * subform 1..1 integer "subform"
  * datatype 1..1 integer "datatype"
  * code 1..1 string "code"
* subfield 0..* BackboneElement "subfield"
  * num 1..1 integer "num"
  * field 1..1 integer "field"
  * datatype 1..1 integer "datatype"
  * code 1..1 string "code"
* data 0..* BackboneElement "data"
  * instance 1..1 integer "instance" "identifiant de l'instance de formulaire"
  * num 1..1 integer "num" "Identifiant de la question"
  * subfield 0..1 integer "subfield" "Identifiant de la sous question (tableau)"
  * choicevalue 0..1 integer "choicevalue" "Réponse de type référence ou choice"
  * integervalue 0..1 integer "integervalue" "Réponse de type nombre entier"