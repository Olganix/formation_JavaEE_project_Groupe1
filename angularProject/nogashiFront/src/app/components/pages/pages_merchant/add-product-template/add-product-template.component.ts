import {Component, OnInit, Output} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ConnexionService} from '../../../../services/connexion.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../../../classes/user';
import {RestResponse} from '../../../../classes/rest-response';
import {CustomValidators} from '../../../../validators/custom-validators';
import {MerchantService} from '../../../../services/merchant.service';
import {UserService} from '../../../../services/user.service';

@Component({
  selector: 'app-add-product-template',
  templateUrl: './add-product-template.component.html',
  styleUrls: ['./add-product-template.component.scss']
})
export class AddProductTemplateComponent implements OnInit {

  @Output() productTemplate: ProductTemplate;

  form1: FormGroup;

  id: FormControl;
  name: FormControl;
  description: FormControl;
  externalCode: FormControl;
  isPackaged: FormControl;
  price: FormControl;
  salePrice: FormControl;
  timeControlStatus: FormControl;
  // schedulerWeekForSaleAndUnsold: FormControl;   // Todo
  maxDurationCart: FormControl;
  image: FormControl;
  private defaultImage = 'NoProduct.jpg';
  // _productDetails  // Todo
  // List<Commerce> commerces // todo


  constructor(private merchantService: MerchantService,
              private userService: UserService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder
        ) { }

  ngOnInit() {

    const id = (this.route.snapshot.params.id !== '') ? Number(this.route.snapshot.params.id) : 0;


    this.id = new FormControl(id, [ Validators.required ]);
    this.name = new FormControl(null, [ Validators.required ]);
    this.description = new FormControl(null, [ Validators.required ]);
    this.externalCode = new FormControl(null, [ Validators.required ]);
    this.isPackaged = new FormControl(true, []);
    this.price = new FormControl(null, [ Validators.required ]);
    this.salePrice = new FormControl(null, [ Validators.required ]);
    this.timeControlStatus = new FormControl(true, []);
    this.maxDurationCart = new FormControl(20, [ Validators.required ]);
    this.image = new FormControl(null, [ Validators.required ]);


    this.form1 = this.fb.group({
      id : this.id,
      name : this.name,
      description : this.description,
      externalCode: this.externalCode,
      isPackaged: this.isPackaged,
      price: this.price,
      salePrice: this.salePrice,
      timeControlStatus: this.timeControlStatus,
      maxDurationCart: this.maxDurationCart,
      image: this.image
    });


    // recuperation les info pour l'update.
    if (id !== 0) {
      if ((this.merchantService.lastProductTemplate) && (this.merchantService.lastProductTemplate.id === id)) {               // on a gardé le dernier pour eviter les problemes dans les changement de page.
        this.__setFormData(this.merchantService.lastProductTemplate);
      } else {

        this.userService.getProductTemplateById(id).subscribe(    // le + c'est pour caster un string en number
          (rrp: RestResponse) => {

            if (rrp.status === 'SUCCESS') {
              this.__setFormData(new ProductTemplate(rrp.data));
            } else {
              console.log('Echec de la recuperation de la liste des fiches produits : ' + rrp.errormessage);
            }
          },
          error => {
            console.log('Echec de la recuperation de la liste des fiches produits : ', error);
          });
      }
    }
  }

  private __setFormData(pt: ProductTemplate) {

    this.name.setValue(pt.name);
    this.description.setValue(pt.description);
    this.externalCode.setValue(pt.externalCode);
    this.isPackaged.setValue(pt.isPackaged);
    this.price.setValue(pt.price);
    this.salePrice.setValue(pt.salePrice);
    this.timeControlStatus.setValue(pt.timeControlStatus);
    this.maxDurationCart.setValue(pt.maxDurationCart);
    this.defaultImage = pt.image;
  }


  onSubmit() {
    if (this.form1.valid) {

      console.log('form:');
      console.log(this.form1.value);

      const pt = new ProductTemplate();
      pt.setFromAddForm(this.form1.value.id, this.form1.value.name, this.form1.value.description, this.form1.value.externalCode, this.form1.value.isPackaged, this.form1.value.price, this.form1.value.salePrice, this.form1.value.timeControlStatus, this.form1.value.maxDurationCart,  ((this.form1.value.image !== null) && (this.form1.value.image.trim() !== '') ) ? this.form1.value.image : this.defaultImage);

      this.merchantService.addOrUpdateProductTemplate( pt ).subscribe(
        (rrp: RestResponse) => {

          console.log('addOrUpdateProductTemplate: ');
          console.log(rrp);

          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'La sauvegarde de la fiche produit a bien été effectuée.', 10);

            const ptRet = new ProductTemplate(rrp);
            this.router.navigate(['/productSheet/' + ptRet.id]);

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de la sauvegarde de la fiche produit : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de la sauvegarde de la fiche produit : ' + error, 10);
        });
    }
  }


  public controlName(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le nom du produit est obligatoire`;
      }
    }
    return null;
  }

  public controlImage(): string {
    if (this.image.touched) {
      if (this.image.hasError('required')) {
        return `Une image du produit est obligatoire`;
      }
    }
    return null;
  }

  public controlPrice(): string {
    if (this.price.touched) {
      if (this.price.hasError('required')) {
        return `Le prix du produit est obligatoire`;
      }
    }
    return null;
  }

  public controlSalePrice(): string {
    if (this.salePrice.touched) {
      if (this.salePrice.hasError('required')) {
        return `Le prix en promotion du produit est obligatoire`;
      }
    }
    return null;
  }


  public controlDescription(): string {
    if (this.description.touched) {
      if (this.description.hasError('required')) {
        return `Une description du produit est obligatoire`;
      }
    }
    return null;
  }

  public controlMaxDurationCart(): string {
    if (this.maxDurationCart.touched) {
      if (this.maxDurationCart.hasError('required')) {
        return `Une durée maximale du produit dans le panier est obligatoire`;
      }
    }
    return null;
  }
}
