# Cursor jump repo for reagent and shadow dom

More info here: https://github.com/reagent-project/reagent/pull/627

```bash
npm install
npx shadow-cljs watch app
```

You can now then open [http://localhost:8020](http://localhost:8020) and test the issue.


## Testing the solution 

Now checkout branch `fix` and do the same thing again:

```bash 
npm install 
npx shadow-cljs watch app 
```

This time it uses a reagent fork with the fix applied and the cursor no longer jumps.
