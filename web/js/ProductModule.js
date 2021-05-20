class ProductModule{
    async printProducts(){
        var result = "";
        let response = await fetch('listProductsJson', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json;charset:utf-8' }
          });
          if (response.ok) {
            result = await response.json();
          } else {
            document.getElementById('info').innerHTML = 'Ошибка сервера';
            return null;
          }
          console.log(result);
        var output = `<div class="card m-2" style="min-width: 12rem;">`
        for(let product of result){
            output+= 
            `<!--<img src="insertFile/"  class="card-img-top" alt="..." style="max-width: 12rem; max-height: 15rem">-->
             <div class="card-body">
               <h5 class="card-title"> ${product.name} </h5>
               <p class="card-text">Цена:${product.price}  </p>
               <p class="card-text">Кол-во:${product.count} </p>
               <p class="card-text"><span></span> <br></p>
             </div>`;
        }
        
        
        document.getElementById("content").innerHTML = output;
    }
    printAddProductForm(){
        document.getElementById("content").innerHTML = `
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="name" name="name" value="">
                    </div>
                  </div>
                  <div class="mb-3 row">
                    <label for="price" class="col-sm-3 col-form-label">Цена:</label>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="price" name="price" value="">
                    </div>
                  </div>
                  <div class="mb-3 row">
                    <label for="amount" class="col-sm-3 col-form-label">Кол-во: </label>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" name="amount" id="amount" value="">
                    </div>
                <div class="col-sm-12">
                  <button  class="btn btn-primary mb-3 w-100" id="add">Отправить</button>
                </div>
                  </div>`;
        document.getElementById("add").addEventListener('click', productModule.addProduct);
    }
    async addProduct(){
        const name = document.getElementById("name").value;
        const price = document.getElementById("price").value;
        const amount = document.getElementById("amount").value;
        const credential= {
            "name": name,
            "price": price,
            "amount": amount
        };
        const response = await fetch('addProductJson', {
         method: 'POST',
         headers: {
          'Content-Type': 'application/json;charset:utf8'
         },
         body: JSON.stringify(credential)
       });
       
       if(response.ok){
        const result = await response.json();
        document.getElementById('info').innerHTML = result.info;
        console.log("Request status: "+result.requestStatus);
        document.getElementById('content').innerHTML='';
        if(result.requestStatus){
          sessionStorage.setItem('token',JSON.stringify(result.token));
          sessionStorage.setItem('role',JSON.stringify(result.role));
        }else{
          if(sessionStorage.getItem(token) !== null){
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('role');
          }
        }
      }else{
        console.log("Ошибка получения данных");
      }
      
    }
}

let productModule = new ProductModule();
export {productModule};

