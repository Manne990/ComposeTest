
if git status --porcelain | grep .; then
   echo "Commiting and pushing changes to remote"
   exit 1
fi

echo "No changes to commit"