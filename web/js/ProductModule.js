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
               <img src="insertFile/${product.picture}">
               <p class="card-text"><span></span> <br></p>
             </div>`;
        }
        
        
        document.getElementById("content").innerHTML = output;
    }
    printAddProductForm(){
        document.getElementById("content").innerHTML = `
        <h3 class="w-100 text-center my-5 ">Добавить новый продукт</h3>
        <form id="productForm" method="POST" enctype="multipart/form-data">
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
              Название 
          </div>
          <div class="col-8 text-start ">
            <input class="w-100" type="text" name="name" id="name">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
            Кол-во
          </div>
          <div class="col-8 text-start">  
            <input class="col-8" type="text" name="amount" id="amount">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">   
              Цена
          </div>
          <div class="col-8 text-start">  
            <input class="col-4" type="text" name="price" id="price">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
              Загрузите обложку 
          </div>
          <div class="col-8 text-start">     
              <input class="form-control col" type="file" name="file" id="file-cover">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
        <div class="col-4 text-end">
        </div>
        <div class="col-8 text-start mt-3">     
          <input class="w-50 bg-primary text-white" type="submit" id="add" name="submit" value="Добавить">
        </div>
      </div>
      </form>
        `;
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
         body: new FormData(document.getElementById('productForm'))//JSON.stringify(credential)
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

