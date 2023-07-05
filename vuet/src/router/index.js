import store from '@/store'
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path:'/login',
    name:'Login',
    component:() => import('../components/Login.vue')
  },
  {
    path:'/register',
    name:'Register',
    component: ()=> import('../components/views/MyRegister.vue')
  },
  {
    path:'/404',
    name:'404',
    component: ()=> import('../components/views/error/404.vue')
  },
  {
    path:'/front',
    name:'Front',
    component :()=> import('../components/views/front/Front.vue'),
    redirect:"/frontHome",
    children:[
      {
        path:'/frontHome',
        name:'FrontHome',
        component:()=> import('../components/views/front/Home.vue')
      },
      {
        path:'/item1',
        name:'Item1',
        component:()=> import('../components/views/front/Item1.vue')
      },
      {
        path:'/frontPassword',
        name:'frontPassword',
        component:()=> import('../components/views/front/Password.vue')
      },
      {
        path:'/frontPerson',
        name:'frontPerson',
        component:()=> import('../components/views/front/Person.vue')
      },
      {
        path:'/frontVideo',
        name:'FrontVideo',
        component:()=> import('../components/views/front/FrontVideo.vue'),
      },
      {
        path:'/fromGood',
        name:'FromGood',
        component:()=> import('../components/views/front/FromGood.vue')
      },
      {
        path:'/fromBook/:id',
        name:'FromBook',
        component:()=> import('../components/views/front/FromBook.vue')
      },
      {
        path:'/formhhh/:id',
        name:'Formhhh',
        component:()=> import('../components/views/front/Fromhhh.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  if(storeMenus){
    const mangeRouter = {
      path:'/',
      name:'Mange',
      component:()=> import('../components/views/Manage.vue'),
      redirect:"/home",
      children:[
        {
          path:'/person',
          name:'个人信息',
          component: ()=> import('../components/views/Person.vue')
        },
        {
          path:'/password',
          name:'修改信息',
          component :()=> import('../components/views/Password.vue')
        },
      ]
    }
    const menus = JSON.parse(storeMenus);
    console.log(menus);
    menus.forEach(item => {
      if(item.path){
        // 当path不为空的时候 才去显示路由
        let itemMenu = {path:item.path.replace("/",""),name:item.name,component:()=> import('../components/views/'+item.pagePath+'.vue')}
        console.log(item.pagePath)
        mangeRouter.children.push(itemMenu);
      }else if(item.children.length){
        item.children.forEach(item => {
          if(item.path){
            let itemMenu = {path:item.path.replace("/",""),name:item.name,component:()=> import('../components/views/'+item.pagePath+'.vue')}
            mangeRouter.children.push(itemMenu);
          }
        })
      }
      })

      // 获取当前的路由对象名称数组
      const currentRoutes = router.getRoutes().map(v => v.name);
      if(!currentRoutes.includes('Mange')){
        // 动态添加到现在的路由对象去
         router.addRoute(mangeRouter);
      }
  }
}

// 重置 我就在set一次路由
setRoutes();



router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
  store.commit("setPath")
  
   // 未找到路由的情况
   if (!to.matched.length) {
    const storeMenus = localStorage.getItem("menus")
    if (storeMenus) {
      next("/404")
    } else {
      // 跳回登录页面
      next("/login")
    }
  }
  // 其他的情况都放行
  next()


})

export default router
